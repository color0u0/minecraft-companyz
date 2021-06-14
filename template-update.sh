git remote add template https://github.com/donghune/minecraft-plugin-sample.git

git fetch --all

git cherry-pick template/master

git remote remove template
