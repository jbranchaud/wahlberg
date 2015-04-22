(defproject wahlberg "0.1.0-SNAPSHOT"
  :description "markov chain text generator written in clojure"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot wahlberg.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
