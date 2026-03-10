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

# Modul 3 Maintainability & OO Principles #

## Refleksi ##
1. Explain what principles you apply to your project!

**Single Responsibility Principle (SRP)**.
Saya memisahkan CarController dari ProductController dengan membuat file terpisah. Hal ini karena SRP mengharuskan setiap class memiliki satu tanggung jawab saja. ProductController dan CarController memiliki tanggung jawab yang berbeda.

**Liskov Substitution Principle (LSP)**.
Saya menghapus inheritance antara CarController dan ProductController. Sebelumnya, CarController merupakan child dari ProductController, tetapi tidak dapat menggantikan ProductController tanpa menyebabkan masalah pada program. Karena itu, hubungan inheritance tersebut dihilangkan agar sesuai dengan prinsip LSP.

**Dependency Inversion Principle (DIP)**.
Saya menerapkan DIP dengan menggunakan CarService(interface) di CarController, bukan langsung menggunakan CarServiceImpl. Selain itu, saya juga membuat CarRepositoryInterface agar CarServiceImpl bergantung pada abstraction(interface), bukan pada CarRepository yang merupakan concrete class.

2. Explain the advantages of applying SOLID principles to your project with examples.

**Kode lebih mudah dipelihara**.
Dengan menerapkan SRP pada CarController dan ProductController, jika ada perubahan pada logic Car, saya hanya perlu mengubah CarController tanpa takut ProductController terpengaruh.

**Lebih mudah dikembangkan**.
Dengan menerapkan DIP pada CarController yang bergantung pada CarService (interface), jika ingin menganti implemetasi service, saya hanya perlu mengganti implementasinya saja.

3. Explain the disadvantages of not applying SOLID principles to your project with examples.

**Kode sulit dipelihara dan cepat jadi berantakan**.
Tanpa SRP, jika ProductController mengurus logic termasuk Car, setiap perubahan kecil dapat merusak fitur lain.

**Sulit dikembangkan**.
Tanpa DIP, jika ingin mengganti implementasi service, saya harus mengubah kode di banyak tempat sehingga dapat meningkatkan risiko bug.


# Modul 4 Refactoring and TDD #
1. Reflect based on Percival (2017) proposed self-reflective questions (in “Principles and Best
   Practice of Testing” submodule, chapter “Evaluating Your Testing Objectives”), whether this
   TDD flow is useful enough for you or not. If not, explain things that you need to do next time
   you make more tests.

TDD ini lumayan berguna karena test dibuat lebih dulu sehingga implementasi lebih terarah. Namun ada kekurangan dimana beberapa edge case baru disadari setelah test awal dibuat. Saya harus lebih teliti menganalisis spesifikasi secara menyeluruh sebelum mulai menulis test pertama agar semua skenario sudah terpetakan sejak awal.

2. You have created unit tests in Tutorial. Now reflect whether your tests have successfully
   followed F.I.R.S.T. principle or not. If not, explain things that you need to do the next time you
   create more tests.

Secara umum test sudah mengikuti prinsip F.I.R.S.T. Test berjalan Fast karena menggunakan Mockito sehingga tidak hit database, Independent karena setiap test memiliki data sendiri, Repeatable karena tidak bergantung environment eksternal, dan Self-validating karena menggunakan `assert` dan `verify` yang jelas hasilnya, dan Timely karena test sudah ditulis sebelum implementasi sesuai alur TDD.



