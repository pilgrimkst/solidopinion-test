package finder.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CapmaignIndexPopulator {
    public static final Logger LOGGER = LoggerFactory.getLogger(CapmaignIndexPopulator.class);

    public static CampaignIndex build(InputStream data) {
        LOGGER.info("Reading data from stream");

        return null;
    }

    public static CampaignIndex build(File f) throws IOException {
        LOGGER.info("Creating finder from data in file {}", f.getAbsolutePath());
        return build(new FileInputStream(f));
    }


}
