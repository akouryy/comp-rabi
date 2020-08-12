package rabi

import java.io.File
import scopt.{DefaultOParserSetup, OParser}

object Main:
  val parser =
    val builder = OParser.builder[Config]
    import builder._
    OParser.sequence(
      programName("rabi"),
      arg[String]("<problem>")
        .required()
        .action((x, c) => c.copy(problem = x))
        .text("the problem ID"),
      opt[File]('i', "input")
        .action((x, c) => c.copy(outputSpecified = Some(x)))
        .text("input file name (default: <problem>.cpp)"),
      opt[File]('o', "output")
        .action((x, c) => c.copy(outputSpecified = Some(x)))
        .text("output file name (default: <problem>.gen.cpp)"),
      opt[Unit]('v', "verbose")
        .action((_, c) => c.copy(verbose = true))
        .text("whether to output verbosely"),
      help('h', "help"),
    )
  end parser

  def main(args: Array[String]): Unit =
    val parsed = OParser.parse(parser, args, Config(), new DefaultOParserSetup {
      override def showUsageOnError = Some(true)
    })

    for config <- parsed do
      val in = ut.Files.read(config.input)
      ut.Files.write(config.output, in)
      println(fansi.Color.Cyan(s"generated ${config.output}"))

end Main
