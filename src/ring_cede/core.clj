(ns ring-cede.core
  (:require
    [ring.util.io :refer [piped-input-stream]]
    [ring.util.response :refer [response content-type]]
    [compojure.response :refer [Renderable]])
  (:import
    [javax.imageio ImageIO]
    [java.awt.image RenderedImage]
    [java.io OutputStream]))


(defn- png-writer [^RenderedImage img ^OutputStream out]
  (ImageIO/write img "png" out)
  (.flush out))

(extend-protocol Renderable
  RenderedImage
  (render [this _]
    (->
      (partial png-writer this)
      piped-input-stream
      response
      (content-type "image/png"))))
