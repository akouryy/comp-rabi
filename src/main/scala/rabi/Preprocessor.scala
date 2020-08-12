package rabi

import java.io.File
import scala.collection.mutable
import scala.util.matching.Regex

final case class Preprocessor(
  fn: PartialFunction[(String, Preprocessor.Env), Unit],
):
  def apply(e: Preprocessor.Env)(): Unit =
    fn.lift(e.entry.elem.content, e)

object Preprocessor:
  type Entry = ut.DoublyLinkedList[Rabi.Line]#Entry

  final class Env(
    val entry: Entry,
    val config: Config,
    val loadedFile: mutable.Set[String] = mutable.Set.empty,
    val typeChars: mutable.Map[Char, (String, Int)] = mutable.Map.empty,
  ):
    def remove(): Unit = entry.remove()

  def all(env: Env)(): Unit =
    for
      pp <- Seq(
        includeRabi,
        registerTypeChar,
        remove,
      )
    do
      pp(env)()

  private val includeRabi =
    val regex = """ ^ \s* #include \s* " (\w+.hpp) " \s* $ """.rx
    Preprocessor:
      case (regex(header), env) =>
        val headerFile = File("lib/" + header)

        if !env.loadedFile(headerFile.getCanonicalPath)
          env.loadedFile += headerFile.getCanonicalPath
          val headerContent = ut.Files.read(headerFile).strip
          env.entry.insertAfter(Rabi.convertToLines(headerContent, headerFile).reverse: _*)

        env.remove()
  end includeRabi

  private val registerTypeChar =
    val regex =
      """ ^ \s* #pragma \s+ R \s+ typechar \s+ (\w) \s+ (\w+) \s+ (\d+) \s* $ """.rx
    Preprocessor:
      case (regex(ch, str, arity), env) =>
        env.typeChars(ch(0)) = (str, arity.toInt)
        env.remove()

  private val remove =
    val pragmaOnce = """ ^ \s* #pragma \s+ once \s* $ """.rx
    Preprocessor:
      case (pragmaOnce(), env) => env.remove()
      case ("", env) if env.entry.elem.file != env.config.input.getName => env.remove()

end Preprocessor
