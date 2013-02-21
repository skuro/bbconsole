(ns bbconsole.commands
  (:require [clojure.pprint :as pp]))

(def portal-request (atom {:host "localhost"
                           :port "7777"
                           :protocol "http"
                           :context "/portalserver"
                           :portals []}))

; TODO: just a bit of an input validation
(defn string-input [in]
  (str in))

(defn quit []
  (println "Done.")
  (System/exit 0))

(defn dummy [& _]
  (println "Sample calculation, 1 + 2 =" (+ 1 2)))

(defn request-param [k in]
  (swap! portal-request assoc k (string-input in)))

(defn select-host [host]
  (request-param :host host)
  (println (str "Ok, selected '" host "'")))

(defn select-portal [portal]
  (if (some #{portal} (:portals @portal-request))
    (do (request-param :portal portal)
        (println (str "Ok, selected '" portal "'")))
    (println (str "No portal named '" portal "' found on the current host"))))

(defn create-portal [portal]
  (swap! portal-request #(assoc % :portals (conj (:portals %) portal)))
  (println (str "Ok, created portal"))
  (select-portal portal))

(defn inspect []
  (pp/pprint @portal-request))
