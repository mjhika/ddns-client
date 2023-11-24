(ns ddns.dns.cloudflare.interface
  (:require
   [ddns.ip.interface :as ip]
   [cheshire.core :as json]
   [hato.client :as hc]))

(def cloudflare-base-url "https://api.cloudflare.com/client/v4")

(defn cloudflare-update-dns-record-request-map
  [token email ip record-name record-comment ttl]
  {:headers {"X-Auth-Email" email}
   :oauth-token token
   :content-type :json
   :form-params {:content ip
                 :name record-name
                 :type "A"
                 :comment record-comment
                 :ttl ttl}})

(defn cloudflare-get-dns-records-map
  [token email]
  {:headers {"X-Auth-Email" email}
   :oauth-token token})

(defn cloudflare-update-dns-record-request-url [zone-id record-id]
  (format "%s/zones/%s/dns_records/%s" cloudflare-base-url zone-id record-id))

(defn cloudflare-get-dns-records-url [zone-id]
  (format "%s/zones/%s/dns_records" cloudflare-base-url zone-id))

(comment
  (let [ip (ip/ip-from-ipify)
        url (cloudflare-update-dns-record-request-url
             ""
             "")
        request (cloudflare-update-dns-record-request-map
                 ""
                 ""
                 ip
                 ""
                 "Updated from Clojure DDNS"
                 1)]
    (-> (hc/put url request)
        :body
        (json/parse-string keyword)))

  (let [url (cloudflare-get-dns-records-url "")
        request (cloudflare-get-dns-records-map
                 ""
                 "")]
    (-> (hc/get url request)
        :body
        (json/parse-string keyword))))
