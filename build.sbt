name := "poc-finch-tls"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "com.twitter" %% "finagle-http" % "6.42.0"
libraryDependencies += "com.github.finagle" %% "finch-core" % "0.13.1"
libraryDependencies += "io.circe" %% "circe-generic" % "0.7.0"
libraryDependencies += "com.github.finagle" %% "finch-circe" % "0.13.1"
libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.2"

/*
*
*         <shapeless.version>2.3.2</shapeless.version>
        <finch.version>0.13.1</finch.version>
        <circe.version>0.7.0</circe.version>
        <finagle.version>6.42.0</finagle.version>

* */