package com.vortexa.refinery.example.extraction

import com.vortexa.refinery.WorkbookParser
import com.vortexa.refinery.cell.StringHeaderCell
import com.vortexa.refinery.dsl.SheetParserDefinition
import com.vortexa.refinery.dsl.TableParserDefinition
import com.vortexa.refinery.dsl.WorkbookParserDefinition
import com.vortexa.refinery.result.GenericParsedRecord
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

class DividerExample {

    @Test
    fun `should extract the data from the table with dividers`() {
        // given
        val headerColumns = setOf(
            StringHeaderCell("team"),
            StringHeaderCell("plays"),
            StringHeaderCell("points")
        )
        val definition = WorkbookParserDefinition(
            spreadsheetParserDefinitions = listOf(
                SheetParserDefinition(
                    sheetNameFilter = { it == "table with dividers" },
                    tableDefinitions = listOf(
                        TableParserDefinition(
                            requiredColumns = headerColumns,
                            hasDivider = true
                        )
                    )
                )
            )
        )

        // and
        val file = File(
            javaClass.classLoader.getResource("examples/basic_examples.xlsx")!!.file
        )

        // when
        val parsedRecords  = WorkbookFactory.create(file).use { WorkbookParser(definition, it).parse() }

        // then
        assertThat(parsedRecords)
            .hasSize(8)
            .containsExactly(
                // data after the 1st divider "Group A"
                GenericParsedRecord(
                    mapOf(
                        "spreadsheet_name" to "table with dividers",
                        "row_number" to 3,
                        "divider" to "Group A",
                        "team" to "PSG",
                        "plays" to 3,
                        "points" to 7,
                    )
                ),
                GenericParsedRecord(
                    mapOf(
                        "spreadsheet_name" to "table with dividers",
                        "row_number" to 4,
                        "divider" to "Group A",
                        "team" to "Manchester City",
                        "plays" to 3,
                        "points" to 6,
                    )
                ),
                GenericParsedRecord(
                    mapOf(
                        "spreadsheet_name" to "table with dividers",
                        "row_number" to 5,
                        "divider" to "Group A",
                        "team" to "Club Brugge",
                        "plays" to 3,
                        "points" to 4,
                    )
                ),
                GenericParsedRecord(
                    mapOf(
                        "spreadsheet_name" to "table with dividers",
                        "row_number" to 6,
                        "divider" to "Group A",
                        "team" to "RB Leipzig",
                        "plays" to 3,
                        "points" to 0,
                    )
                ),
                // data after the 2nd divider "Group B"
                GenericParsedRecord(
                    mapOf(
                        "spreadsheet_name" to "table with dividers",
                        "row_number" to 8,
                        "divider" to "Group B",
                        "team" to "Liverpool",
                        "plays" to 3,
                        "points" to 9,
                    )
                ),
                GenericParsedRecord(
                    mapOf(
                        "spreadsheet_name" to "table with dividers",
                        "row_number" to 9,
                        "divider" to "Group B",
                        "team" to "Atletico Madrid",
                        "plays" to 3,
                        "points" to 4,
                    )
                ),
                GenericParsedRecord(
                    mapOf(
                        "spreadsheet_name" to "table with dividers",
                        "row_number" to 10,
                        "divider" to "Group B",
                        "team" to "FC Porto",
                        "plays" to 3,
                        "points" to 4,
                    )
                ),
                GenericParsedRecord(
                    mapOf(
                        "spreadsheet_name" to "table with dividers",
                        "row_number" to 11,
                        "divider" to "Group B",
                        "team" to "AC Milan",
                        "plays" to 3,
                        "points" to 0,
                    )
                ),
            )
    }
}
