package org.chatbot.stt.adaptation;


import java.io.File;

public class TrainerConfiguration {

    private final String sourceFile;
    private final String outputFolder;
    private final String sampleFilePrefix;
    private final int sampleFileZeroPaddingSize;
    private final String fileIdsName;
    private final String fileTranscriptionName;


    private TrainerConfiguration(String sourceFile, String outputFolder, String sampleFilePrefix,
                                 int sampleFileZeroPaddingSize, String fileIdsName, String fileTranscriptionName) {
        this.sourceFile = sourceFile;
        this.outputFolder = outputFolder;
        this.sampleFilePrefix = sampleFilePrefix;
        this.sampleFileZeroPaddingSize =sampleFileZeroPaddingSize;
        this.fileIdsName = fileIdsName;
        this.fileTranscriptionName = fileTranscriptionName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public String getSampleFilePrefix() {
        return sampleFilePrefix;
    }

    public int getSampleFileZeroPaddingSize() {
        return sampleFileZeroPaddingSize;
    }

    public String getFileIdsName() {
        return fileIdsName;
    }

    public String getFileTranscriptionName() {
        return fileTranscriptionName;
    }

    public static class Builder {
        private static final String ADAPTATION_RESOURCES_FOLDER = "stt/target/classes/adaptation";
        private static final String DEMO_FILE_NAME = "sentences-demo.txt";
        public static final String DEMO_FILE = ADAPTATION_RESOURCES_FOLDER + File.separator + DEMO_FILE_NAME;
        public static final String OUTPUT_FOLDER = ADAPTATION_RESOURCES_FOLDER + File.separator + "training-data";

        public static final String SAMPLE_FILE_PREFIX = "sample";
        public static final int SAMPLE_FILE_ZERO_PADDING = 4;
        public static final String FILE_IDS_CONTAINER_NAME = "sample.fileids";
        public static final String FILE_TRANSCRIPTION_NAME = "sample.transcription";


        private String sourceFile;
        private String outputFolder;
        private String sampleFilePrefix;
        private int sampleFileZeroPaddingSize;
        private String fileIdsName;
        private String fileTranscriptionName;

        private Builder() {
            setFileIdsName(FILE_IDS_CONTAINER_NAME)
                    .setFileTranscriptionName(FILE_TRANSCRIPTION_NAME)
                    .setOutputFolder(OUTPUT_FOLDER)
                    .setSourceFile(DEMO_FILE)
                    .setSampleFilePrefix(SAMPLE_FILE_PREFIX)
                    .setSampleFileZeroPaddingSize(SAMPLE_FILE_ZERO_PADDING);
        }

        public Builder setSourceFile(String sourceFile) {
            this.sourceFile = sourceFile;
            return this;
        }

        public Builder setOutputFolder(String outputFolder) {
            this.outputFolder = outputFolder;
            return this;
        }

        public Builder setSampleFilePrefix(String sampleFilePrefix) {
            this.sampleFilePrefix = sampleFilePrefix;
            return this;
        }

        public Builder setSampleFileZeroPaddingSize(int sampleFileZeroPaddingSize) {
            this.sampleFileZeroPaddingSize = sampleFileZeroPaddingSize;
            return this;
        }

        public Builder setFileIdsName(String fileIdsName) {
            this.fileIdsName = fileIdsName;
            return this;
        }

        public Builder setFileTranscriptionName(String fileTranscriptionName) {
            this.fileTranscriptionName = fileTranscriptionName;
            return this;
        }

        public TrainerConfiguration build() {
            return new TrainerConfiguration(sourceFile, outputFolder, sampleFilePrefix, sampleFileZeroPaddingSize,
                    fileIdsName, fileTranscriptionName);
        }
    }

}
