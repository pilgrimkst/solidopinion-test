import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CapmaignFinderFactory {
    public static final Logger LOGGER = LoggerFactory.getLogger(CapmaignFinderFactory.class);

    public static CampaignFinder build(InputStream data) {
        LOGGER.info("Reading data from stream");

        return null;
    }

    public static CampaignFinder build(File f) throws IOException {
        LOGGER.info("Creating finder from data in file {}", f.getAbsolutePath());
        return build(new FileInputStream(f));
    }


}
