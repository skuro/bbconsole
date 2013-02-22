(ns bbconsole.xml
  (:import [javax.xml.bind JAXBContext Marshaller]))

(defprotocol Serializable
  (unwrap [this] "Returns the Java object to be passed to the JAXB context")
  (serialize [this ^JAXBContext ctx] "Returns the XML serialization given the provided JAXB context"))

(defn ^JAXBContext new-context
  "Creates a new JAXB context with the provided classes to be bound. They can be
given as a mix of sequences or single instances, e.g.:

  (new-context [ItemRefs ItemRef] GenericList '(PublishState))"
  [& to-bound]
  (let [classes (flatten (mapcat vector to-bound))]
    (JAXBContext/newInstance (into-array classes))))

(extend-protocol Serializable
  Object ;default implementation
  (serialize [this ^JAXBContext ctx]
    (let [^Marshaller marshaller (.createMarshaller ctx)]
      (with-out-str
        (.marshal marshaller this *out*)))))
