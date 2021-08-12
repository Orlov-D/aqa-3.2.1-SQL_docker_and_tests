

<!--
docker-compose up
java -jar artifacts/app-deadline.jar -P:jdbc.url=jdbc:mysql://localhost:3306/app -P:jdbc.user=app -P:jdbc.password=pass
docker-compose exec mysql mysql -u app app -p
SELECT * FROM users;

git init
git remote add origin https://github.com/netology-git/demo.git
git add .gitignore
git add -f artifacts/app-deadline.jar
git add *
git commit -am "Initial commit"
git push --set-upstream origin master
-->