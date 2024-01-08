(ns ddns.system.dispatch
  (:require
   [ddns.dns.cloudflare.interface :as cloudflare]))

(defn- schedule [f interval]
  (future (while true
            (do (Thread/sleep interval)
                (f)))))

(defmulti dispatch-system :ddns-provider)

(defmethod dispatch-system :default
  [conf]
  (schedule #(when (cloudflare/update-record? conf)
               (cloudflare/update-record conf)
               (println "Record updated..."))
            300000))

(comment
  (def c (dispatch-system @ddns.system/system-config))

  (future-cancel c))
