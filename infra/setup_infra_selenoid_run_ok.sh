cd ..

teamcity_tests_directory=$(pwd)
echo "Current dir teamcity_tests_directory: $teamcity_tests_directory"
workdir="teamcity_tests_infrastructure"
teamcity_server_workdir="teamcity_server"
#teamcity_agent_workdir="teamcity_agent"
#selenoid_workdir="selenoid"
selenoid_workdir="selenoid;C"
teamcity_server_container_name="teamcity_server_instance"
#teamcity_agent_container_name="teamcity_agent_instance"
selenoid_container_name="selenoid_instance"
selenoid_ui_container_name="selenoid_ui_instance"

#создаём список workdir_names
#workdir_names=($teamcity_server_workdir $teamcity_agent_workdir $selenoid_workdir)
workdir_names=($teamcity_server_workdir $teamcity_agent_workdir)

#создаём список container_names
container_names=($teamcity_server_container_name $teamcity_agent_container_name $selenoid_container_name $selenoid_ui_container_name)

########################################################################################################################
printf "\n%s\n"
echo "Request IP"
export ip=$(powershell -Command "(Get-NetIPAddress -InterfaceAlias (Get-NetConnectionProfile).InterfaceAlias -AddressFamily IPv4).IPAddress")
echo "Current IP: $ip"

########################################################################################################################
printf "\n%s\n"
echo "Delete previous run data"

rm -rf $workdir
mkdir $workdir
cd $workdir

echo "Current dir: $(pwd)"
#для каждой директории в workdir_names создаём соответствующую директорию
for dir in "${workdir_names[@]}"; do
  mkdir $dir
done

#создаём директорию etc и в ней selenoid
mkdir etc
cd etc
mkdir  $selenoid_workdir
cd ..

echo "Current dir after creating selenoid: $(pwd)"

#для каждого контейнера в container_names: остановить и удалить
for container in "${container_names[@]}"; do
  docker stop $container
  docker rm $container
done

########################################################################################################################
printf "\n%s\n"
echo "Start teamcity server"

cd $teamcity_server_workdir
echo "Current dir: $(pwd)"
docker run -d --name $teamcity_server_container_name \
    -v $(pwd)/logs:/opt/teamcity/logs \
    -p 8111:8111 \
    jetbrains/teamcity-server

echo "Teamcity Server is running..."
########################################################################################################################
#printf "\n%s\n"
#echo "Start teamcity agent"
#cd ../$teamcity_agent_workdir
#echo "Current dir: $(pwd)"
#docker run -d --name $teamcity_agent_container_name \
#    -e SERVER_URL="http://$ip:8111"
#    -v $(pwd)/conf:/data/teamcity_agent/conf \
#    -p 8111:8111 \
#    jetbrains/teamcity-agent

#echo "Teamcity Agent is running..."
########################################################################################################################
printf "\n%s\n"
#selenoid поднимется для тех браузеров, которые указаны в browsers.json
#cd .. && cd $selenoid_workdir

echo "Start selenoid"
echo "Current dir: $(pwd)"
cd ../etc/
echo "Current dir: $(pwd)"
#mkdir config
#копируем browsers.json в папку config
#cp $teamcity_tests_directory/infra/browsers.json config/
#копируем browsers.json в папку selenoid
#cp $teamcity_tests_directory/infra/browsers.json selenoid/
cp $teamcity_tests_directory/infra/browsers.json "selenoid;C"/

#browsers="$(pwd)/config/browsers.json"
#echo "browsers.json path: $browsers"
#echo "browsers.json:"
#cat $browsers


echo $selenoid_workdir
echo "Current dir: $(pwd)"




#> $current = $PWD -replace "\\", "/" -replace "C", "c"  (1)
#> docker run -d                                         `
#--name selenoid                                         `
#-p 4444:4444                                            `
#-v //var/run/docker.sock:/var/run/docker.sock           `
#-v ${current}/config/:/etc/selenoid/:ro                 `
#aerokube/selenoid:latest-release



#> $current = $PWD -replace "\\", "/" -replace "C", "c"

docker run -d --name $selenoid_container_name \
           -p 4444:4444 \
           -v //var/run/docker.sock:/var/run/docker.sock \
           -v /c/Users/Marina/AquaProjects/teamcity-testing-framework/teamcity_tests_infrastructure/etc/"selenoid;c":/etc/selenoid:ro \
           aerokube/selenoid:latest-release



#-v $current/etc/selenoid:/etc/selenoid:ro \

# -v /c/users/marina/aquaprojects/teamcity-testing-framework/teamcity_tests_infrastructure/selenoid/config/:/etc/selenoid/:ro \
# C:\Users\Marina\AquaProjects\teamcity-testing-framework\teamcity_tests_infrastructure\selenoid\config
#           -v $(pwd)/config/:/etc/selenoid/:ro \



#для запуска selenoid должны быть скачаны все образы браузеров
#чтобы спарсить имена образов браузеров из browsers.json по регулярному выражению и складывем их в список
#image_names=($(awk -F'"' '/"image": "/{print $4}' "$(pwd)/config/browsers.json"))
#image_names=($(awk -F'"' '/"image": "/{print $4}' "$(pwd)/selenoid/browsers.json"))
image_names=($(awk -F'"' '/"image": "/{print $4}' "$(pwd)/selenoid;C/browsers.json"))



echo "Pull all browser images: $image_names"
#скачиваем соответсвующие образы
for image in "${image_names[@]}"; do
  docker pull $image
done

########################################################################################################################
printf "\n%s\n"
echo "Start selenoid-ui"
echo "Current dir: $(pwd)"
docker run -d --name $selenoid_ui_container_name \
           -p 8080:8080 \
           aerokube/selenoid-ui:latest-release \
           --selenoid-uri "http://$ip:4444"

########################################################################################################################
printf "\n%s\n"
echo "Setup teamcity server"

#запустить startUpTest из класса SetupTest
cd .. && cd ..
echo "Current dir: $(pwd)"
mvn clean test -Dtest=SetupTest#startUpTest

########################################################################################################################
printf "\n%s\n"
echo "Parse superuser token"

#C:\Users\Marina\AquaProjects\teamcity-testing-framework\teamcity_tests_infrastructure\teamcity_server\logs;C

#superuser_token=$(grep -o 'Super user authentication token: [0-9]*' $teamcity_tests_directory/infra/$workdir/$teamcity_server_workdir/logs/teamcity-server.log | awk '{print $NF}')
#superuser_token=$(grep -o 'Super user authentication token: [0-9]*' $teamcity_tests_directory/infra/$workdir/$teamcity_server_workdir/"logs;c"/teamcity-server.log | awk '{print $NF}')
superuser_token=$(grep -o 'Super user authentication token: [0-9]*' $teamcity_tests_directory/$workdir/$teamcity_server_workdir/"logs;c"/teamcity-server.log | awk '{print $NF}')
echo "Super user token: $superuser_token"

########################################################################################################################
printf "\n%s\n"
echo "Run system tests"
#cd .. && cd .. && cd ..
echo "Current dir: $(pwd)"
#заполнить файл config.properties
#echo "host=$ip:8111\nsuperUserToken=$superuser_token\nremote=true" > $teamcity_tests_directory/src/main/resources/config.properties
config="$teamcity_tests_directory/src/main/resources/config.properties"
echo "host=$ip:8111" > $config
echo "superUserToken=$superuser_token" >> $config
echo "remote=http://$ip:4444/wd/hub" >> $config
echo "browser=firefox" >> $config

#для вывода содержимого файла config.properties
#cat $teamcity_tests_directory/src/main/resources/config.properties
echo $config
ls "$config"
cat $config

printf "\n%s\n"
echo "Run API tests"
echo "Current dir: $(pwd)"
mvn test -DsuiteXmlFile=testng-suites/api-suite.xml

printf "\n%s\n"
echo "Run UI tests"
echo "Current dir: $(pwd)"
mvn test -DsuiteXmlFile=testng-suites/ui-suite.xml


read -p "Press any key to continue" x