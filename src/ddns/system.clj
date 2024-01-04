(ns ddns.system
  (:require
   [aero.core :as aero]
   [babashka.fs :as fs])
  (:import
   (java.io FileNotFoundException)))

(def system-config
  "This atom acts as the database for the system. See the project's 
  documentation for supported values."
  (let [conf-file "acdc.config.edn"]
    (if (fs/exists? conf-file)
      (atom (aero/read-config (fs/file conf-file)))
      (throw (FileNotFoundException.
              (format "%s not found." conf-file))))))

(comment
  @system-config)
