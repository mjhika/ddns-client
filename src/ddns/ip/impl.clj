(ns ddns.ip.impl
  (:require
   [clojure.core.async :as async])
  (:import
   (org.xbill.DNS DClass
                  Message
                  Name
                  Record
                  SimpleResolver
                  Type)))

(defn- ip-from [server absolute-record t]
  (fn []
    (let [query-record (Record/newRecord (Name/fromString absolute-record)
                                         t
                                         DClass/IN)
          query-message (Message/newQuery query-record)
          resolver (SimpleResolver. server)
          message (str (.getSection (.send resolver query-message) 1))]
      (re-find #"\d+.\d+.\d+.\d+" message))))

(def ^:private ip-from-opendns-dns (ip-from "resolver1.opendns.com"
                                            "myip.opendns.com."
                                            Type/A))
(def ^:private ip-from-google-dns (ip-from "ns1.google.com"
                                           "o-o.myaddr.l.google.com."
                                           Type/TXT))
(def ^:private ip-from-akamai-dns (ip-from "ns1-1.akamaitech.net"
                                           "whoami.akamai.net."
                                           Type/ANY))

(defn get-ip []
  (let [c1 (async/chan)
        c2 (async/chan)
        c3 (async/chan)
        out (async/chan)]
    (async/thread
      (let [[v _] (async/alts!! [c1 c2 c3])]
        (async/go (async/>! out v))))
    (async/thread
      (async/>!! c1 (ip-from-opendns-dns))
      (async/>!! c2 (ip-from-google-dns))
      (async/>!! c3 (ip-from-akamai-dns)))
    (async/<!! out)))

(comment
  (time (ip-from-opendns-dns))
  (time (ip-from-google-dns))
  (time (ip-from-akamai-dns))

  (time (get-ip)))
