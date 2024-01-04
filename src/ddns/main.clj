(ns ddns.main
  (:gen-class)
  (:require
   [ddns.system :as d.sys]))

(set! *warn-on-reflection* true)

(comment
  @d.sys/config)
