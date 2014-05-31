package tpbScraper.util

class ClassPathUtil {
    static InputStream getResourceAsStream(String resourceName) {
        Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName)
    }

    static File getResourceAsFile(String resourceName) {
        new File(Thread.currentThread().getContextClassLoader().getResource(resourceName).file)
    }
}
