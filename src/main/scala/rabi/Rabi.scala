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
    program ++= convertToLines(original, config.input)

    for entry <- program do
      includeRabi(entry) || registerTypeChar(entry)

    println(typeChars)

    program.mapToSeq(e => s"${e.elem.content}\n").mkString

  private def convertToLines(str: String, file: File): Seq[Line] =
    str.linesIterator.zipWithIndex.map((c, i) => Line(file.getName, i + 1, c)).toSeq

  private def includeRabi(entry: program.Entry): Boolean =
    entry.elem.content match
      case Rabi.IncludeRabi(header) =>
        val headerFile = File("lib/" + header)
        if !loaded(headerFile.getCanonicalPath)
          loaded += headerFile.getCanonicalPath

          val headerContent = ut.Files.read(headerFile).strip
          convertToLines(headerContent, headerFile).reverseIterator.foreach(entry.insertAfter)

        entry.remove()
        true

      case Rabi.PragmaOnce() =>
        entry.remove()
        true
      case _ => false
  end includeRabi

  private def registerTypeChar(entry: program.Entry): Boolean =
    entry.elem.content match
      case Rabi.RegisterTypeChar(ch, str, arity) =>
        typeChars(ch(0)) = (str, arity.toInt)
        entry.remove()
        true
      case _ => false

end Rabi

object Rabi:
  final case class Line(file: String, index: Int, content: String):
    override def toString: String = s"$file:$index"

  val IncludeRabi = """ ^ \s* #include \s* " (\w+.hpp) " \s* $ """.replace(" ", "").r
  val PragmaOnce = """ ^ \s* #pragma \s+ once \s* $ """.replace(" ", "").r
  val RegisterTypeChar =
    """ ^ \s* #pragma \s+ R \s+ typechar \s+ (\w) \s+ (\w+) \s+ (\d+) \s* $ """.replace(" ", "").r

end Rabi
