def projectProperties = [
	[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', numToKeepStr: '5']],
	pipelineTriggers([cron('@daily')])
]

properties(projectProperties)

def SUCCESS = hudson.model.Result.SUCCESS.toString()

currentBuild.result = 'UNKNOWN'

try {
	parallel check: {
		stage('Check') {
			node {
				checkout scm
				try {
					sh "./gradlew clean check --refresh-dependencies --no-daemon"
					currentBuild.result = SUCCESS
				}
				catch (Exception cause) {
					currentBuild.result = 'FAILED: check'
					throw cause
				}
				finally {
					junit '**/build/test-results/test/*.xml'
				}
			}
		}
	}

	if (currentBuild.result.equals(SUCCESS)) {
		parallel artifacts: {
			stage('Deploy Artifacts') {
				node {
					checkout scm
					withCredentials([file(credentialsId: 'spring-signing-secring.gpg', variable: 'SIGNING_KEYRING_FILE')]) {
						withCredentials([string(credentialsId: 'spring-gpg-passphrase', variable: 'SIGNING_PASSWORD')]) {
							withCredentials([usernamePassword(credentialsId: 'oss-token', passwordVariable: 'OSSRH_PASSWORD', usernameVariable: 'OSSRH_USERNAME')]) {
								withCredentials([usernamePassword(credentialsId: '02bd1690-b54f-4c9f-819d-a77cb7a9822c', usernameVariable: 'ARTIFACTORY_USERNAME', passwordVariable: 'ARTIFACTORY_PASSWORD')]) {
									sh "./gradlew deployArtifacts finalizeDeployArtifacts -Psigning.secretKeyRingFile=$SIGNING_KEYRING_FILE -Psigning.keyId=$SPRING_SIGNING_KEYID -Psigning.password='$SIGNING_PASSWORD' -PossrhUsername=$OSSRH_USERNAME -PossrhPassword=$OSSRH_PASSWORD -PartifactoryUsername=$ARTIFACTORY_USERNAME -PartifactoryPassword=$ARTIFACTORY_PASSWORD --refresh-dependencies --no-daemon --stacktrace"
								}
							}
						}
					}
				}
			}
		}
	}
}
finally {

	def buildStatus = currentBuild.result
	def buildNotSuccess =  !SUCCESS.equals(buildStatus)
	def lastBuildNotSuccess = !SUCCESS.equals(currentBuild.previousBuild?.result)

	if (buildNotSuccess || lastBuildNotSuccess) {

		stage('Notifiy') {
			node {

				final def RECIPIENTS = [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']]

				def subject = "${buildStatus}: Build ${env.JOB_NAME} ${env.BUILD_NUMBER} status is now ${buildStatus}"
				def details = """The build status changed to ${buildStatus}. For details see ${env.BUILD_URL}"""

				emailext (
					subject: subject,
					body: details,
					recipientProviders: RECIPIENTS,
					to: "$GEODE_TEAM_EMAILS"
				)
			}
		}
	}
}
