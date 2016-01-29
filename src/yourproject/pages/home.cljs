(ns yourproject.pages.home
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]

            [yourproject.util :as util]
            [yourproject.parsers.home :as home-parser]))

;; -----------------------------------
;; Om-next component for the home page.
(defui HomePage
  static om/IQuery
  (query [this]
         [:title :count])
  Object
  (render [this]
          (let [props (om/props this)
                {:keys [title
                        count]} props]
            
            (dom/div nil
                     (dom/h2 nil title)
                     (dom/span nil (str "Home (count): " count))
                     (dom/button #js {:type "button"
                                      :onClick (fn [e]
                                                 (om/transact! this
                                                               `[(home/increment ~{})]))}
                                 "Incr!")))))

(def home-page (om/factory HomePage))
