package li.cil.oc.util

import java.io.{BufferedReader, InputStreamReader}
import java.nio.charset.StandardCharsets
import scala.collection.mutable.BitSet

import li.cil.oc.OpenComputers

object FontUtils {
  private val defined_double_wide: BitSet = BitSet()

  // theoretical Unicode maximum
  val codepoint_limit: Int = 0x110000
  def wcwidth(charCode: Int): Int = if (defined_double_wide(charCode)) 2 else 1

  {
    val table = Array[Int](
      16,16,16,18,19,20,21,22,23,24,25,26,27,28,29,30,31,16,16,32,16,16,16,33,34,35,
      36,37,38,39,16,16,40,16,16,16,16,16,16,16,16,16,16,16,41,42,16,16,43,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,44,16,45,46,47,48,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,49,16,16,50,51,16,52,16,16,
      16,16,16,16,16,16,53,16,16,16,16,16,54,55,16,16,16,16,56,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,57,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,58,59,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,0,0,0,0,0,0,0,0,0,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,255,255,255,255,255,255,255,255,255,
      255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,
      255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,248,3,0,0,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,254,255,255,255,255,191,
      182,0,0,0,0,0,0,0,31,0,255,7,0,0,0,0,0,248,255,255,0,0,1,0,0,0,0,0,0,0,0,0,0,
      0,192,191,159,61,0,0,0,128,2,0,0,0,255,255,255,7,0,0,0,0,0,0,0,0,0,0,192,255,
      1,0,0,0,0,0,0,248,15,0,0,0,192,251,239,62,0,0,0,0,0,14,0,0,0,0,0,0,0,0,0,0,0,
      0,0,0,0,0,240,255,255,127,7,0,0,0,0,0,0,20,254,33,254,0,12,0,0,0,2,0,0,0,0,0,
      0,16,30,32,0,0,12,0,0,0,6,0,0,0,0,0,0,16,134,57,2,0,0,0,35,0,6,0,0,0,0,0,0,16,
      190,33,0,0,12,0,0,0,2,0,0,0,0,0,0,144,30,32,64,0,12,0,0,0,4,0,0,0,0,0,0,0,1,
      32,0,0,0,0,0,0,0,0,0,0,0,0,0,192,193,61,96,0,12,0,0,0,0,0,0,0,0,0,0,144,64,48,
      0,0,12,0,0,0,0,0,0,0,0,0,0,0,30,32,0,0,12,0,0,0,0,0,0,0,0,0,0,0,0,4,92,0,0,0,
      0,0,0,0,0,0,0,0,242,7,128,127,0,0,0,0,0,0,0,0,0,0,0,0,242,27,0,63,0,0,0,0,0,0,
      0,0,0,3,0,0,160,2,0,0,0,0,0,0,254,127,223,224,255,254,255,255,255,31,64,0,0,0,
      0,0,0,0,0,0,0,0,0,224,253,102,0,0,0,195,1,0,30,0,100,32,0,32,0,0,0,0,0,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,224,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
      28,0,0,0,28,0,0,0,12,0,0,0,12,0,0,0,0,0,0,0,176,63,64,254,15,32,0,0,0,0,0,56,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,135,1,4,
      14,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,128,1,0,0,0,0,0,0,64,
      127,229,31,248,159,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15,0,0,0,0,0,208,23,4,0,0,
      0,0,248,15,0,3,0,0,0,60,11,0,0,0,0,0,0,64,163,3,0,0,0,0,0,0,240,207,0,0,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,247,255,253,33,16,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
      0,0,0,0,0,0,0,0,0,255,255,255,255,127,0,0,240,0,248,0,0,0,124,0,0,0,0,0,0,31,
      252,0,0,0,0,0,0,0,0,0,0,0,0,255,255,255,255,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,128,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,128,0,0,0,0,
      0,0,0,0,0,0,0,0,255,255,255,255,0,0,0,0,0,60,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,128,247,63,0,0,0,128,0,0,0,0,0,
      0,0,0,0,0,3,0,68,8,0,0,96,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,16,0,0,0,255,
      255,3,0,0,0,0,0,192,63,0,0,128,255,3,0,0,0,0,0,7,0,0,0,0,0,200,19,0,0,0,0,0,0,
      0,0,0,0,0,0,0,126,102,0,8,16,0,0,0,0,0,0,0,0,0,0,0,0,157,193,2,0,0,0,0,48,64,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,32,33,0,0,0,0,0,64,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,255,255,0,0,127,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,128,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,14,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,32,110,240,0,0,0,0,0,135,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
      0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,255,127,0,0,0,0,0,0,0,3,0,0,0,0,0,120,38,0,0,
      0,0,0,0,0,0,7,0,0,0,128,239,31,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,192,127,0,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,40,191,0,0,0,0,0,0,0,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,128,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
      0,0,0,0,0,128,3,248,255,231,15,0,0,0,60,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
      28,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
    )
    val wtable = Array[Int](
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,18,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,19,16,16,16,16,16,16,16,16,16,16,20,21,22,23,24,17,
      17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,25,
      17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,
      17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,
      17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,
      17,17,17,17,17,17,17,17,26,16,16,16,16,27,16,16,17,17,17,17,17,17,17,17,17,17,
      17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,17,
      17,17,17,17,17,17,17,28,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,17,17,16,16,16,29,30,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,31,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,
      16,16,16,16,32,16,16,16,16,16,16,16,16,16,16,16,16,16,0,0,0,0,0,0,0,0,0,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,255,255,255,255,255,255,255,255,255,
      255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,
      255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,0,0,0,0,0,0,0,
      0,248,0,0,0,0,0,0,0,0,0,0,252,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,255,255,255,251,255,255,255,
      255,255,255,255,255,255,255,15,0,255,255,255,255,255,255,255,255,255,255,255,
      255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,63,0,0,0,255,15,
      255,255,255,255,255,255,255,127,254,255,255,255,255,255,255,255,255,255,127,
      254,255,255,255,255,255,255,255,255,255,255,255,255,224,255,255,255,255,63,
      254,255,255,255,255,255,255,255,255,255,255,127,255,255,255,255,255,7,255,255,
      255,255,15,0,255,255,255,255,255,127,255,255,255,255,255,0,255,255,255,255,
      255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,127,255,
      255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,
      255,255,255,255,0,0,0,0,0,0,0,0,255,255,255,255,255,255,255,255,255,255,255,
      255,255,255,255,255,255,31,255,255,255,255,255,255,127,0,0,0,0,0,0,0,0,0,0,0,
      0,0,0,0,0,0,0,0,255,255,255,31,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,255,255,255,
      255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,15,0,255,
      255,127,248,255,255,255,255,255,15,0,0,255,3,0,0,255,255,255,255,247,255,127,
      15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,254,255,255,255,255,255,255,255,255,
      255,255,255,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,127,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,255,255,255,255,255,7,255,1,3,0,0,
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
    )
    def c_wcwidth(charCode: Int): Int = {
      if (charCode < 0xFF) {
        if (((charCode + 1) & 0x7F) >= 0x21) 1
        else if (charCode > 0) -1
        else 0
      }
      else if ((charCode & 0xfffeffff) < 0xfffe) {
        if (((table(table(charCode >> 8) * 32 + ((charCode & 0xFF) >> 3)) >> (charCode & 7)) & 1) == 1) 0
        else if (((wtable(wtable(charCode >> 8) * 32 + ((charCode & 0xFF) >> 3)) >> (charCode & 7)) & 1) == 1) 2
        else 1
      }
      else if ((charCode & 0xfffe) == 0xfffe) -1
      else if ((charCode - 0x20000) < 0x20000) 2
      else if ((charCode == 0xe0001) || ((charCode - 0xe0020) < 0x5f) || ((charCode - 0xe0100) < 0xef)) 0
      else 1
    }
    OpenComputers.log.info("Initializing unicode wcwidth.")
    for (i <- 0 until codepoint_limit) {
      if (c_wcwidth(i) == 2)
        defined_double_wide += i
    }
    try {
      OpenComputers.log.info("Initializing font glyph widths.")
      val font = FontUtils.getClass.getResourceAsStream("/assets/opencomputers/font.hex")
      try {
        var line: String = null
        val input = new BufferedReader(new InputStreamReader(font, StandardCharsets.UTF_8))
        var out_of_range_glyph: Int = 0
        while ({line = input.readLine; line != null}) {
          val info = line.split(":")
          val charCode = Integer.parseInt(info(0), 16)
          if (charCode >= 0 && charCode < codepoint_limit) {
            info(1).trim.length match {
              case 64 => defined_double_wide += charCode
              case 32 => defined_double_wide -= charCode
              case n => OpenComputers.log.warn(s"Invalid glyph size detected in font.hex. Expected 64 or 32, got: $n")
            }
          } else {
            if (out_of_range_glyph == 0) {
              OpenComputers.log.warn(f"Invalid glyph char code detected in font.hex. Expected 0<= charCode <$codepoint_limit%X, got: $charCode%X")
            }
            out_of_range_glyph += 1
          }
        }
        if (out_of_range_glyph > 1) {
          OpenComputers.log.warn(f"${out_of_range_glyph} total invalid glyph char codes detected in font.hex")
        }
      } finally {
        try {
          font.close()
        } catch {
          case ex: Throwable => OpenComputers.log.error(s"Error closing font.hex: $ex")
        }
      }
    } catch {
      case ex: Throwable => OpenComputers.log.error(s"Error parsing glyphs to determine widths: $ex")
    }
    OpenComputers.log.info("glyph width ready.")
  }
}
