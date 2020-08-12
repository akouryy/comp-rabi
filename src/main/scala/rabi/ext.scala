package rabi

import scala.util.matching.Regex

extension (s: String)
  def rx: Regex = s.replaceAll("""\s++""", "").r
