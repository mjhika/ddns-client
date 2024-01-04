(ns ddns.dns.cloudflare.interface
  (:require
   [ddns.dns.cloudflare.impl :as cf.impl]
   [ddns.system :as d.sys]))

(defn update-record [conf]
  (cf.impl/update-record conf))

(defn update-record? [conf]
  (cf.impl/update-record? conf))

(comment
  (update-record @d.sys/system-config)

  (update-record? @d.sys/system-config))
