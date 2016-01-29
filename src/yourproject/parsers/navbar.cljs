(ns yourproject.parsers.navbar
  (:require [om.next :as om]
            [yourproject.reconciler :refer [mutate read]]))

(defmethod read :navbar
  [{:keys [state query]} k _]

  (let [st @state]
    {:value (om/db->tree query (get st k) st)}))
