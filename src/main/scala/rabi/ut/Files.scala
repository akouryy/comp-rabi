package rabi
package ut

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Files => NIOFiles}
import scala.io.Source
import scala.util.Using

object Files:
  def read(file: File): String =
    Using.resource(Source.fromFile(file))(_.mkString)

  def write(file: File, content: String): Unit =
    NIOFiles.write(file.toPath, content.getBytes(StandardCharsets.UTF_8))
