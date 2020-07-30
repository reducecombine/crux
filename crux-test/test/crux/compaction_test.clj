(ns crux.compaction-test
  (:require [clojure.test :as t]
            [crux.api :as crux]
            [crux.fixtures :as fix]
            [crux.fixtures.jdbc :as fj]
            [crux.node :as n]))

(t/use-fixtures :each fj/with-h2-opts)

(t/deftest test-compaction-leaves-replayable-log
  (let [tx (with-open [api (n/start fix/*opts*)]
             (crux/submit-tx api [[:crux.tx/put {:crux.db/id :foo}]])
             (Thread/sleep 10) ; to avoid two txs at the same ms
             (crux/submit-tx api [[:crux.tx/put {:crux.db/id :foo}]]))]

    (with-open [api2 (n/start fix/*opts*)]
      (crux/await-tx api2 tx nil)
      (t/is (= 2 (count (crux/entity-history (crux/db api2) :foo :asc)))))))
