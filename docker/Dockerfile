FROM gradle:latest

ENV ANDROID_HOME /usr/local/android-sdk
ENV PATH $ANDROID_HOME/tools/bin:$PATH

WORKDIR $ANDROID_HOME
ADD android-sdk-license $ANDROID_HOME/licenses/

USER root
RUN wget https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip \
&&  unzip sdk-tools-linux-4333796.zip \
&&  rm sdk-tools-linux-4333796.zip \
&&  sdkmanager "platform-tools" "tools"

RUN sdkmanager "build-tools;28.0.3" "platforms;android-28"

USER gradle