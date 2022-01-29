package com.wcc17;

/**
 * Created by wcc17 on 3/9/17.
 */
public class LabelBuilder {

    static final int MAX_LINE_WIDTH = 50;

    public static String buildLabel(Vine vine) {
        StringBuilder vineLabelBuilder = new StringBuilder();
        vineLabelBuilder.append("<html>");
        vineLabelBuilder.append("index: ");
        vineLabelBuilder = appendData(vineLabelBuilder, String.valueOf(vine.index));
        vineLabelBuilder = appendData(vineLabelBuilder, vine.created);
        vineLabelBuilder = appendData(vineLabelBuilder, vine.description);
        vineLabelBuilder = appendData(vineLabelBuilder, vine.likes);
        vineLabelBuilder = appendData(vineLabelBuilder, vine.loops);
        vineLabelBuilder = appendData(vineLabelBuilder, vine.username);
//    	vineLabelBuilder = appendData(vineLabelBuilder, vine.venueAddress);
//    	vineLabelBuilder = appendData(vineLabelBuilder, vine.venueCity);
//    	vineLabelBuilder = appendData(vineLabelBuilder, vine.venueCountryCode);
//    	vineLabelBuilder = appendData(vineLabelBuilder, vine.venueName);
//    	vineLabelBuilder = appendData(vineLabelBuilder, vine.venueState);
        vineLabelBuilder.append("</html>");

        return vineLabelBuilder.toString();
    }

    private static StringBuilder appendData(StringBuilder dataStringBuilder, String labelValue) {
        if(labelValue.length() > MAX_LINE_WIDTH) {
            labelValue = buildMultiLineLabelData(labelValue);
        }

        dataStringBuilder.append(labelValue);
        dataStringBuilder.append("<br>");

        return dataStringBuilder;
    }

    private static String buildMultiLineLabelData(String labelValue) {
        StringBuilder multiLineLabelBuilder = new StringBuilder();

        int i = 0;
        while(i < labelValue.length()) {
            int endIndex = (i + MAX_LINE_WIDTH);
            if(endIndex > (labelValue.length() - 1)) {
                endIndex = labelValue.length() - 1;
            }

            String line = labelValue.substring(i, endIndex);
            multiLineLabelBuilder.append(line);

            i += MAX_LINE_WIDTH;
            if(i < labelValue.length()) {
                multiLineLabelBuilder.append("<br>");
            }
        }

        return multiLineLabelBuilder.toString();
    }
}
