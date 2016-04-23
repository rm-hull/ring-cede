(ns ring-cede.core-test
  (:require
    [clojure.java.io :as io]
    [clojure.test :refer :all]
    [compojure.response :as response]
    [digest]
    [ring-cede.core])
  (:import
    [javax.imageio ImageIO]))

(deftest check-img-render
  (let [png "test/data/basn2c16.png"
        img (ImageIO/read (io/as-file png))
        res (response/render img {})]

    (testing "expected keys"
      (is (= [:status :headers :body] (keys res))))

    (testing "status code"
      (is (= 200 (res :status))))

    (testing "headers"
      (is (= {"Content-Type" "image/png"} (res :headers))))

    (testing "body"
      (is (= "968ae1560826be163088cea24a8a5a7b"
             (digest/md5 (res :body)))))))
