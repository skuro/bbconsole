(ns bbconsole.rest.portal
  (:require [clj-http.client :as client]
            [bbconsole.xml :as xml])
  (:import com.backbase.portal.foundation.domain.model.Portal
           [com.backbase.portal.foundation.domain.conceptual
            PropertyDefinition
            StringPropertyValue]
           [com.backbase.portal.foundation.domain.publishing
            ItemRefs]))

(defn create-url [ctx path]
  (str (:protocol ctx)
       "://"
       (:host     ctx)
       ":"
       (:port ctx)
       (:context ctx)
       path))

(def jaxb-context
  (xml/new-context Portal
                   ItemRefs))

(defn string-prop [name value]
  (PropertyDefinition. name (StringPropertyValue. value)))

(def default-props
  {"DefaultLandingPage" (string-prop "DefaultLandingPage" "index")
   "LoginPage"          (string-prop "LoginPage" "/login/login.jsp")
   "LogoutPage"         (string-prop "LogoutPage" "/login/logout.jsp")
   "ErrorPage"          (string-prop "ErrorPage" "/login/error.jsp")
   "AccessDeniedPage"   (string-prop "AccessDeniedPage" "/login/login.jsp?login_error=accessdenied")
   "AuthenticationFailurePage" (string-prop "AuthenticationFailurePage" "/login/login.jsp?login_error=failure")
   "TargetedDevice"     (string-prop "TargetedDevice" "mixed")
   "DefaultDevice"      (string-prop "DefaultDevice" "mixed")})

(defn create-portal [ctx]
  {:pre (:portal ctx)}
  (try
    (let [portal (Portal.)
          name   (:portal ctx)
          props  (assoc default-props "title" name)
          _      (.setPropertyDefinitions portal props)
          _      (.setName portal name)
          url    (create-url ctx "/portals.xml")
          result (client/post url {:basic-auth ["admin" "admin"]
                                   :headers    {"Content-Type" "text/xml"}
                                   :body       (xml/serialize portal jaxb-context)})]
      (when (= 201 (:status result))
        (assoc ctx :portals (conj (:portals ctx) name))
        (println (str "Ok, created portal"))))
    (catch Exception e
      (println "Couldn't create the portal:" (.getMessage e))
      ctx)))
