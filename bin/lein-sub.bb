(require '[clojure.tools.cli :as cli]
         '[babashka.process :as p])

(def arg-spec
  [["-s" "--sub-projects sub-projects" "Sub projects"
    :default ["crux-core"
              "crux-rdf"
              "crux-metrics"
              "crux-rocksdb" "crux-lmdb"
              "crux-jdbc"
              "crux-http-client" "crux-http-server"
              "crux-kafka-embedded" "crux-kafka-connect" "crux-kafka"
              "crux-sql"
              "crux-lucene"
              "crux-test"
              "crux-s3"
              "crux-azure-blobs"
              "crux-google-cloud-storage"
              "crux-bench"]
    :parse-fn #(str/split % #":")]])

(let [{{:keys [sub-projects]} :options, :keys [arguments errors]} (cli/parse-opts *command-line-args* arg-spec)]
  (if errors
    (binding [*out* *err*]
      (println errors))

    (doseq [dir sub-projects]
      (println "-- sub:" dir)
      @(p/process (into ["lein"] arguments)
                  {:dir dir
                   :out :inherit
                   :err :inherit}))))
