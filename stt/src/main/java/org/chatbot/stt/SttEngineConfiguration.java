package org.chatbot.stt;


public class SttEngineConfiguration {

    private final String acousticModelPath;
    private final String dictionaryPath;
    private final String languageModelPath;

    public SttEngineConfiguration(final String acousticModelPath, final String dictionaryPath, final String languageModelPath) {
        this.acousticModelPath = acousticModelPath;
        this.dictionaryPath = dictionaryPath;
        this.languageModelPath = languageModelPath;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static SttEngineConfiguration standardConfig() {
        return SttEngineConfiguration.builder()
                .setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us")
                .setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict")
                .setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin").build();
    }

    public static SttEngineConfiguration adaptedConfig(String resourcesPath) {
        return SttEngineConfiguration.builder()
                .setAcousticModelPath(resourcesPath + "en-us-adapt")
                .setDictionaryPath(resourcesPath + "cmudict-en-us.dict")
                .setLanguageModelPath(resourcesPath + "en-us.lm.bin").build();
    }

    public String getAcousticModelPath() {
        return acousticModelPath;
    }

    public String getDictionaryPath() {
        return dictionaryPath;
    }

    public String getLanguageModelPath() {
        return languageModelPath;
    }


    public static class Builder {
        private String acousticModelPath;
        private String dictionaryPath;
        private String languageModelPath;

        private Builder() {}

        public Builder setAcousticModelPath(String acousticModelPath) {
            this.acousticModelPath = acousticModelPath;
            return this;
        }

        public Builder setDictionaryPath(String dictionaryPath) {
            this.dictionaryPath = dictionaryPath;
            return this;
        }

        public Builder setLanguageModelPath(String languageModelPath) {
            this.languageModelPath = languageModelPath;
            return this;
        }

        public SttEngineConfiguration build() {
            return new SttEngineConfiguration(acousticModelPath, dictionaryPath, languageModelPath);
        }
    }

}
