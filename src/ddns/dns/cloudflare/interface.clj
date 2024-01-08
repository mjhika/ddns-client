(ns ddns.dns.cloudflare.interface
  (:require
   [ddns.dns.cloudflare.impl :as cf.impl]))

(defn update-record
  "Given a conf, the function will update the value of the DNS record with the
  public IP address of the host.
  
  Usage:
  (update-record config-map)
  
  Requirements:
  :api-token - used to authenticate to cloudflare
  :auth-username - used to authenticate to cloudflare
  :ddns-record - used to identify the record"
  [conf]
  (cf.impl/update-record conf))

(defn update-record?
  "Given a conf, the function will compare the public IP address of the host
  with the value in the DNS record. If the results match, then the function
  returns false.
  
  Usage:
  (update-record? config-map)
  
  Requirements:
  :ddns-record of the desired domain must be in the conf"
  [conf]
  (cf.impl/update-record? conf))

(comment
  (require '[ddns.system :as d.sys])

  (update-record @d.sys/system-config)

  (update-record? @d.sys/system-config))
