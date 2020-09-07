package rabi

import java.io.File
import scala.collection.mutable
import scala.util.matching.Regex

final class Rabi(original: String)(using config: Config):
  import Rabi.Line

  private val program = ut.DoublyLinkedList[Line]()

  private val loaded = mutable.Set.empty[String]

  private val typeChars = mutable.Map.empty[Char, (String, Int)]

  def generate(): String =
    program ++= Rabi.convertToLines(original, config.input)

    for entry <- program do
      Preprocessor.all(Preprocessor.Env(entry, config, loaded, typeChars))()
      println((entry.removed, "\t", entry.value))

    println(typeChars)

    program.mapToSeq(e => s"${e.value.content}\n").mkString

end Rabi

object Rabi:
  final case class Line(file: String, index: Int, content: String):
    override def toString: String = f"${s"$file:$index"}%-19s $content"

    def shortString: String = s"$file:$index"

  def convertToLines(str: String, file: File): Seq[Line] =
    str.linesIterator.zipWithIndex.map((c, i) => Line(file.getName, i + 1, c)).toSeq
end Rabi
