(defproject bbconsole "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main bbconsole.core
  :aot [bbconsole.core]
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [com.backbase.portal.foundation/domain "5.4.1-SNAPSHOT"]
                 [clj-http "0.6.4"]])
