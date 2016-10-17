package finder.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CampaignIndexer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignIndexer.class);

    public static CampaignIndex buildFromStream(InputStream data) {
        LOGGER.info("Reading data from stream");
        CampaignIndexImpl index = new CampaignIndexImpl();
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(data))) {
            while ((line = br.readLine()) != null) {
                LOGGER.debug("Extracting data from line: {}", line);
                String[] xs = line.split("\\s+");
                Collection<Integer> segments = extractSegments(xs);
                index.addToIndex(xs[0], segments);
            }
        } catch (IOException e) {
            LOGGER.warn("Can't proceed, exception raised {}", e.getMessage());
        }
        LOGGER.info("Index successfully loaded from input stream");
        return index;
    }

    public static CampaignIndex build(File f) throws IOException {
        LOGGER.info("Creating finder from data in file {}", f.getAbsolutePath());
        return buildFromStream(new FileInputStream(f));
    }

    private static Collection<Integer> extractSegments(String[] xs) {
        List<Integer> seg = new ArrayList<>(xs.length - 1);
        for (int i = 1; i < xs.length; i++) {
            seg.add(Integer.parseInt(xs[i]));
        }

        return seg;
    }


}
