# Modul 2 CI/CD dan DevOps #

Dikerjakan oleh:
Inayah Saffanah-2406353276

Link Deployment: [Deployment](https://eshop-b-inayah-saffanah-asri.onrender.com/)

## Refleksi ##
1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them

Code quality issue pertama yang saya benarkan adalah menghapus modifier yang tidak diperlukan. Pada interface ProductService, terdapat modifier public pada tiap methodnya, padahal secara default methodnya sudah public sehingga penulisan modifier public tidak diperlukan. Lalu saya juga menghapus beberapa import yang kurang spesifik, seperti import org.springframework.web.bind.annotation.*, saya spesifikkan importnya menjadi .RequestMapping, .GetMapping, .PostMapping, .PathVariable, dan .ModelAttribute. Dengan beberapa perubahan ini, kodenya menjadi lebih rapih dan efisien.

2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current
implementation has met the definition of Continuous Integration and Continuous
Deployment? Explain the reasons (minimum 3 sentences)!

Berdasarkan workflow CI/CD yang saya miliki, implementasi ini sudah cukup memenuhi definisi CI/CD. Untuk Continuous Integration, workflow ci.yml sudah menjalankan unit test otomatis setiap ada push atau pull request ke branch manapun, dan pmd.yml melakukan static code analysis dengan PMD untuk memastikan kualitas kode memenuhi prinsip CI karena setiap perubahan kode langsung diverifikasi secara otomatis. Untuk Continuous Deployment, Render secara otomatis mendeteksi setiap push ke branch master dan langsung melakukan build serta deploy ulang, sehingga setiap perubahan kode yang lolos CI langsung ter-deploy ke production.




