package rabi

import java.io.File

final case class Config(
  problem: String = "",
  inputSpecified: Option[File] = None,
  outputSpecified: Option[File] = None,
  verbose: Boolean = false,
):
  def input: File = inputSpecified.getOrElse(File(s"$problem.cpp"))

  def output: File = inputSpecified.getOrElse(File(s"$problem.gen.cpp"))
