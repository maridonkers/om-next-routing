(defproject yourproject "0.1.0-SNAPSHOT"
  :description "Your project's description."
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.374"]
                 [org.clojure/clojurescript "1.7.228"]
                 [org.omcljs/om "1.0.0-alpha30"]

                 [secretary "1.2.3"]

                 [com.cemerick/piggieback "0.2.1"]
                 [figwheel-sidecar "0.5.0-3" :scope "test"]]
  
  :plugins [[lein-cljsbuild "1.1.2"]]

  :cljsbuild {
              :builds [{
                        ;; The path to the top-level ClojureScript source directory:
                        :source-paths ["src"]
                        ;; The standard ClojureScript compiler options:
                        ;; (See the ClojureScript compiler documentation for details.)
                        :compiler {
                                   ;; output-to "war/javascripts/main.js"
                                   ;; (default: target/cljsbuild-main.js)
                                   :optimizations :whitespace
                                   :pretty-print true}}]})
