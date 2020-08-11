val dottyVersion = "0.26.0-RC1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "dotty-simple",
    version := "0.1.0",

    scalaVersion := dottyVersion,

    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-language:strictEquality",
      "-migration",
      "-unchecked",
      // "-Yexplicit-nulls",
      "-Yindent-colons",
    ),

    libraryDependencies ++= Seq(
      "com.github.scopt" %% "scopt" % "4.0.0-RC2" withDottyCompat dottyVersion,
      "com.lihaoyi" %% "fansi" % "0.2.7" withDottyCompat dottyVersion,
      "com.novocode" % "junit-interface" % "0.11" % "test",
    )
  )
