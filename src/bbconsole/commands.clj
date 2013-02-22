(ns bbconsole.commands
  (:require [clojure.pprint :as pp]
            [bbconsole.rest.portal :as ptl]))

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
  (swap! portal-request #(assoc % :portal (str portal)))
  (ptl/create-portal @portal-request)
  (swap! portal-request #(assoc % :portals (conj (:portals %) portal))))

(defn inspect []
  (pp/pprint @portal-request))

(defn help []
  (println "Available commands:")
  (let [usage { "(quit)" "stops the program and exits. C-c will work also"
                "(select-portal name)" "selects a portal called name. the portal must exist"
                "(select-host host-or-ip)" "selects the host to operate on [default: localhost]"
                "(create-portal name)" "creates a new portal with the given name"
                "(inspect)" "dumps the current operation context"
                "(help)" "prints this help text"}
        echo (fn [[usage text]]
               (println (str "\t" usage ))
               (println (str "\t" text))
               (println))]
    (dorun
        (map echo usage))))
