(ns ddns.dns.cloudflare.impl
  (:require
   [ddns.ip.interface :as ip]
   [ddns.system :as d.sys]
   [ddns.util :as util]
   [hato.client :as hc])
  (:import
   (org.xbill.DNS Address)))

(def ^:private cloudflare-v4-base-url "https://api.cloudflare.com/client/v4")

(defn- request-auth-map
  [token email]
  {:headers {"X-Auth-Email" email}
   :oauth-token token})

(defn- get-zone [{:keys [api-token auth-username ddns-record]}]
  (let [domain (util/get-domain-name ddns-record)]
    (-> (hc/get (format "%s/zones" cloudflare-v4-base-url)
                (assoc (request-auth-map api-token auth-username)
                       :as :json
                       :query-params {:name domain}))
        :body
        :result
        first)))

(defn- get-record [{:keys [api-token auth-username ddns-record]} zone-id]
  (-> (hc/get (format "%s/zones/%s/dns_records" cloudflare-v4-base-url zone-id)
              (assoc (request-auth-map api-token auth-username)
                     :as :json
                     :query-params {:name ddns-record}))
      :body
      :result
      first))

(defn update-record
  [{:keys [api-token auth-username ddns-record] :as conf}]
  (let [zone-id (:id (get-zone conf))
        record-id (:id (get-record conf zone-id))]
    (hc/put
     (format "%s/zones/%s/dns_records/%s"
             cloudflare-v4-base-url
             zone-id
             record-id)
     (assoc (request-auth-map api-token auth-username)
            :as :json
            :content-type :json
            :form-params {:content (ip/get-ip)
                          :name ddns-record
                          :type "A"
                          :ttl 300
                          :comment (format "Update from ACDC at %s"
                                           (util/new-formatted-dt))}))))

(defn update-record? [{:keys [ddns-record]}]
  (let [current-ip (future (ip/get-ip))
        record-content (:hostAddress (bean (Address/getByName ddns-record)))]
    (not (= @current-ip record-content))))

(comment
  (let [sys-data @d.sys/system-config
        zone (get-zone sys-data)
        record (get-record sys-data
                           (:id zone))]
    {:zone-id (:id zone)
     :record-id (:id record)
     :record-content (:content record)})

  (time (update-record? @d.sys/system-config)))
