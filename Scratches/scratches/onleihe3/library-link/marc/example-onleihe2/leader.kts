import net.avgl.ekz.onleihe.stockapi.model.Stock
import net.avgl.ekz.onleihe.stockapi.model.StockStatus
import org.xbib.marc.Marc
import org.xbib.marc.MarcField
import org.xbib.marc.MarcRecord
import org.xbib.marc.MarcWriter
import org.xbib.marc.label.*
import org.xbib.marc.xml.MarcXchangeWriter
import sun.nio.cs.UTF_8
import java.io.ByteArrayOutputStream
import java.io.StringReader
import java.io.StringWriter
import java.nio.charset.StandardCharsets
import java.time.Instant
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource


class MarcService {

    /**
     * Create records from stocks and transform them to MarcXML
     * @param stocks list of stocks
     * @param titleCountPerPage number of titles per page
     */
    fun transformToMarcXml(stocks: List<Stock>, titleCountPerPage: Int): ByteArray {
        val marcRecord = stocks.map { //TODO titleCountPerPage
            buildRecord(it)
        }
        val outputStream = ByteArrayOutputStream()
        MarcXchangeWriter(outputStream).use { writer ->
            writer.startDocument()
            marcRecord.forEach { writer.record(it) }
            writer.endDocument()
        }
        return outputStream.toByteArray()
    }

    fun transformToMarcIso(stocks: List<Stock>, titleCountPerPage: Int): ByteArray {
        val marcRecord = stocks.map {
            buildRecord(it)
        }
        val byteArrayOutputStream = ByteArrayOutputStream()
        MarcWriter(byteArrayOutputStream, StandardCharsets.UTF_8).use { writer ->
            writer.startDocument()
            marcRecord.forEach { writer.record(it) }
            writer.endDocument()
        }
        return byteArrayOutputStream.toByteArray()
    }

    //TODO validiere, ob richtige zuordnung der tags mit den feldern. Verwende ggf MarcEdit
    private fun buildRecord(stock: Stock): MarcRecord {
        return Marc.builder()
            .recordLabel(LeaderField.createLeaderField())
            .addField(ControlField.createControlNumber(stock.productId))
            .addField(ControlField.createControlNumberIdentifier(stock.onleiheId))
            .addField(ControlField.createDateAndTimeOfLatestTransaction(stock.updated ?: stock.created))
            .addField(ControlField.createGeneralInformation())
            .buildRecord()
    }

    /**
     * Fixed field that comprises the first 24 character positions (00-23) of each bibliographic record and consists
     * of data elements that contain numbers or coded values that define the parameters for the processing of the record.
     */
    private companion object {

        /**
         * Fixed field that comprises the first 24 character positions (00-23) of each bibliographic record and consists
         * of data elements that contain numbers or coded values that define the parameters for the processing of the record.
         */
        object LeaderField {
            fun createLeaderField(): RecordLabel {
                return RecordLabel.builder()
//                    .setRecordLength(5)
                    .setRecordLength(24)
                    .setRecordStatus(RecordStatus.DELETED)
                    .setTypeOfRecord(TypeOfRecord.LANGUAGE_MATERIAL)
                    .setBibliographicLevel(BibliographicLevel.MONOGRAPH)
                    .setTypeOfControl(TypeOfControl.ARCHIVAL)
                    .setEncoding(Encoding.MARC8)
                    .setIndicatorLength(2)
                    .setSubfieldIdentifierLength(2)
                    .setEncodingLevel(EncodingLevel.FULL)
                    .setMultipartResourceRecordLevel(MultipartResourceRecordLevel.NOT_SPECIFIED)
//                    .setDataFieldLength(4)
                    .setDataFieldLength(10)
                    .setStartingCharacterPositionLength(5)
                    .setDescriptiveCatalogingForm(DescriptiveCatalogingForm.NON_ISBD)
                    .build()
            }
        }

        /**
         * Fields 001-008 contain control numbers and other control and coded information that are used in processing
         * MARC bibliographic records. Each control field is identified by a field tag in the Directory and contains either
         * a single data element or a series of fixed-length data elements identified by relative character position.
         * Variable control fields contain neither indicator positions nor subfield codes.
         */
        object ControlField {
            /**
             * Control number assigned by the organization creating, using, or distributing the record.
             * The MARC code for the organization is contained in field 003 (Control Number Identifier).
             */
            fun createControlNumber(controlNumber: String): MarcField {
                return MarcField.builder().tag("001").value(controlNumber).build()
            }

            /**
             * MARC code for the organization whose control number is contained in field 001 (Control Number).
             */
            fun createControlNumberIdentifier(controlNumberIdentifier: String): MarcField {
                return MarcField.builder().tag("003").value(controlNumberIdentifier).build()
            }

            /**
             * Date and time of the latest transaction that has affected the record.
             * Sixteen characters that indicate the date and time of the latest record transaction and serve as
             * a version identifier for the record. They are recorded according to Representation of Dates and Times (ISO 8601).
             * The date requires 8 numeric characters in the pattern yyyymmdd.The time requires 8 numeric characters
             * in the pattern hhmmss.f, expressed in terms of the 24-hour (00-23) clock.
             */
            fun createDateAndTimeOfLatestTransaction(dateAndTimeOfLatestTransaction: Instant): MarcField {
                return MarcField.builder().tag("005").value(dateAndTimeOfLatestTransaction.toString()).build()
            }

            /**
             * 008 FIXED-LENGTH DATA ELEMENTS - - GENERAL INFORMATION
             * Forty character positions (00-39) that provide coded information about the record as a whole and about special
             * bibliographic aspects of the item being cataloged. These coded data elements are potentially useful
             * for retrieval and data management purposes. Data elements are positionally-defined. Character positions
             * that are not defined contain a blank (#). All defined character positions must contain a defined code;
             * for some field 008 positions, this may be the fill character (|). The fill character may be used
             * (in certain character positions) when a cataloging organization makes no attempt to code the character position.
             * The fill character is not allowed in field 008 positions 00-05 (Date entered on file). Its use is discouraged
             * in positions 07-10 (Date 1), 15-17 (Place of publication, production, or execution), and the 008 position defined
             * for Form of item (either position 23 or 29 depending upon the 008 configuration). Character positions 00-17
             * and 35-39 are defined the same across all types of material, with special consideration for position 06.
             * The definition of character positions 18-34 was done independently for each type of material,
             * although certain data elements are defined the same in the specifications for more than one type of material.
             * When similar data elements are defined for inclusion in field 008 for different types of material,
             * they occupy the same field 008 character positions.
             * In the following documentation, character positions are described in the order of their listing at the beginning
             * of this section. Field 008 positions 00-17 and 35-39 (All materials) for all types of material are described first,
             * followed by seven groups of sections for positions 18-34 (Books, Computer files, Maps, Music, Continuing resources,
             * Visual materials and Mixed materials).
             */
            fun createGeneralInformation(): MarcField {
                return MarcField.builder().tag("008").value("      s0000       |||||||||||||||||   ||").build()
            }
        }

    }
}

val stocks = listOf(
    Stock(
        productId="canceledProduct",
        licenceOwnerId= "onleihe-1",
        onleiheId= "onleihe-1",
        activeLicenceCount= 0,
        availableLicenceCount= 0,
        status= StockStatus.OUT_OF_STOCK,
        created= Instant.parse( "2024-06-24T06:33:59.918Z"),
        updated= Instant.parse("2024-07-08T02:03:48.332Z"),
    ),
    Stock(
        productId="maculatedProduct",
        licenceOwnerId= "onleihe-1",
        onleiheId= "onleihe-1",
        activeLicenceCount= 0,
        availableLicenceCount= 1,
        status= StockStatus.MACULATED,
        created= Instant.parse( "2024-06-24T06:33:59.918Z"),
        updated= Instant.parse("2024-07-08T02:03:48.332Z"),
    ),
)

fun formatXml(xml: String): String {
    val transformerFactory = TransformerFactory.newInstance()
    val transformer = transformerFactory.newTransformer().apply {
        setOutputProperty(OutputKeys.INDENT, "yes")
        setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
    }

    val source = StreamSource(StringReader(xml))
    val result = StringWriter()
    transformer.transform(source, StreamResult(result))
    return result.toString()
}

val service = MarcService()
val byteArray = service.transformToMarcXml(stocks, 10)

val marcXmlString = String(byteArray, Charsets.UTF_8)
//val formattedXml = formatXml(marcXmlString)
println(marcXmlString)