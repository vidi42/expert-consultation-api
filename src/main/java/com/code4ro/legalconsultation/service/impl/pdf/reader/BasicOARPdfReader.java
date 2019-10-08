package com.code4ro.legalconsultation.service.impl.pdf.reader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class BasicOARPdfReader implements PDFReader {
    private static final double FIRST_PAGE_REGION_X_POSITION = 0;
    private static final double FIRST_PAGE_REGION_Y_POSITION = 200;
    private static final double FIRST_PAGE_REGION_WIDTH = 700;
    private static final double FIRST_PAGE_REGION_HEIGHT = 550;
    private static final double REGULAR_PAGE_REGION_X_POSITION = 0;
    private static final double REGULAR_PAGE_REGION_Y_POSITION = 100;
    private static final double REGULAR_PAGE_REGION_WIDTH = 480;
    private static final double REGULAR_PAGE_REGION_HEIGHT = 650;
    private static final String FIRST_PAGE_REGION_NAME = "firstPageRegion";
    private static final String REGULAR_PAGE_REGION_NAME = "regularPageRegion";

    private final Rectangle2D firstPageRegion = new Rectangle2D.Double(FIRST_PAGE_REGION_X_POSITION, FIRST_PAGE_REGION_Y_POSITION, FIRST_PAGE_REGION_WIDTH, FIRST_PAGE_REGION_HEIGHT);
    private final Rectangle2D regularPageRegion = new Rectangle2D.Double(REGULAR_PAGE_REGION_X_POSITION, REGULAR_PAGE_REGION_Y_POSITION, REGULAR_PAGE_REGION_WIDTH, REGULAR_PAGE_REGION_HEIGHT);

    public String getContent(PDDocument document) throws IOException {
        final PDFTextStripperByArea stripper = getInitializedPDFTextStripperByArea();
        return getContentAsString(document, stripper);
    }

    private String getContentAsString(final PDDocument document,
                                      final PDFTextStripperByArea stripper) throws IOException {
        final int numberOfPages = document.getNumberOfPages();
        final StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < numberOfPages; i++) {
            resultBuilder.append(getContentAsStringForPage(document, stripper, i));
            resultBuilder.append("\n");
        }

        return resultBuilder.toString();
    }

    private String getContentAsStringForPage(final PDDocument document,
                                             final PDFTextStripperByArea stripper,
                                             final int pageNumber) throws IOException {
        final PDPage page = document.getPage(pageNumber);
        if (pageNumber == 0) {
            stripper.addRegion(FIRST_PAGE_REGION_NAME, firstPageRegion);
            stripper.extractRegions(page);
            final String result = getContentAsStringForRegion(stripper, FIRST_PAGE_REGION_NAME);
            stripper.removeRegion(FIRST_PAGE_REGION_NAME);
            return result;
        }

        stripper.extractRegions(page);
        return getContentAsStringForRegion(stripper, REGULAR_PAGE_REGION_NAME);
    }

    private String getContentAsStringForRegion(final PDFTextStripperByArea stripper, final String regionName) {
        final String textForRegion = stripper.getTextForRegion(regionName);
        return textForRegion.trim();
    }

    private PDFTextStripperByArea getInitializedPDFTextStripperByArea() throws IOException {
        final PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.addRegion(REGULAR_PAGE_REGION_NAME, regularPageRegion);

        return stripper;
    }
}
