(ns ip
  (:require
   [cheshire.core :as json]
   [hato.client :as hc]))

(defn ip-from-x [url]
  (fn []
    (let [req (hc/get url)]
      (when (= (:status req) 200)
        (:body req)))))

(defn parse-ip [json]
  (-> json
      (json/parse-string keyword)
      :ip))

(def ip-from-dnsomatic (ip-from-x "https://myip.dnsomatic.com"))
(def ip-from-opendns #(parse-ip ((ip-from-x "https://myipv4.p1.opendns.com/get_my_ip"))))
(def ip-from-myip #(parse-ip ((ip-from-x "https://api.myip.com"))))
(def ip-from-ipify (ip-from-x "https://api.ipify.org"))

(comment
  (ip-from-opendns)
  (ip-from-myip)
  (ip-from-dnsomatic)
  (ip-from-ipify))
