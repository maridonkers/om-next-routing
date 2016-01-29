(ns yourproject.pages.browse
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]

            [yourproject.util :as util]
            [yourproject.parsers.browse :as browse-parser]))

;; -----------------------------------
;; Om-next component for the browse page.
(defui BrowsePage
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
                     (dom/span nil (str "Browse (count): " count))
                     (dom/button #js {:type "button"
                                      :onClick (fn [e]
                                                 (om/transact! this
                                                               `[(browse/increment ~{})]))}
                                 "Incr!")))))

(def browse-page (om/factory BrowsePage))
