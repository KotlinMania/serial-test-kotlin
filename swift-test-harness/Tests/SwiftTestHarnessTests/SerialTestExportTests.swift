import Testing
import SerialTest

@Suite("SerialTest Swift Export Tests")
struct SerialTestExportTests {
    @Test("Swift module loads cleanly")
    func testSwiftModuleLoads() throws {
        #expect(Bool(true), "SerialTest swift module imported cleanly")
    }
}
