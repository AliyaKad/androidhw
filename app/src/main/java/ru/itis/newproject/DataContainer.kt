package ru.itis.newproject


object DataContainer {
    private val allItems = listOf(
        Item("first_id", "Собака", "https://i.pinimg.com/originals/76/2c/dc/762cdcc21c11048ae8725635d22827ba.jpg", "Это домашнее животное, известное своей преданностью."),
        Item("second_id", "Кошка", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4mB6qxKTqeJ5H08DhnjbJybh_WUXq40LPiw&s", "Кошки - независимые и грациозные существа."),
        Item("third_id", "Хомяк", "https://upload.wikimedia.org/wikipedia/commons/4/4b/KoreaHamster.jpg", "Маленький и пушистый питомец, который активен ночью."),
        Item("fourth_id", "Кролик", "https://i.pinimg.com/originals/03/75/68/037568ee10df8b3ff231d0d584dd4ca1.jpg", "Кролики - милые и пушистые, любят морковку."),
        Item("fifth_id", "Мышь", "https://avatars.mds.yandex.net/i?id=71839b96df0de08ba1585f85248eb4b3e8a1d7f2-5242719-images-thumbs&n=13", "Маленькие грызуны, которые могут быть домашними питомцами."),
        Item("sixth_id", "Крыса", "https://avatars.mds.yandex.net/i?id=09f613045f5c81348ba4eff1aa23ed4fb040dc04af7474dd-4697805-images-thumbs&n=13", "Умные и социальные существа, которые могут быть отличными питомцами."),
        Item("first_id", "Ёж", "https://img.freepik.com/free-photo/cute-baby-hedgehog-closeup-moss-with-black-background_488145-1549.jpg", "Маленькое и колючее млекопитающее, которое часто встречается в садах."),
        Item("second_id", "Рыбка", "https://cache3.youla.io/files/images/780_780/5e/f6/5ef6e73f67768e56e10939ce.jpg", "Популярный аквариумный питомец, известный своими яркими цветами."),
        Item("third_id", "Попугай", "https://vesiskitim.ru/static/records/443b72e014c64f4cac3ad556f9f725d9.jpeg", "Яркая птица, которая может имитировать звуки и человеческую речь."),
        Item("fourth_id", "Мини пиг", "https://avatars.mds.yandex.net/i?id=0806a5eca677aee17429a1b031bffd445913265653e8de7a-4234782-images-thumbs&n=13", "Маленькая свинка, которая становится все более популярной как домашний питомец."),
        Item("fifth_id", "Змея", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Crotalus_cerastes_mesquite_springs_CA-2.jpg/550px-Crotalus_cerastes_mesquite_springs_CA-2.jpg", "Рептилия, известная своей способностью к маскировке и разнообразием видов."),
        Item("sixth_id", "Медведь", "https://avatars.mds.yandex.net/i?id=27b1f2c71cc657f9cf20765cd3765d361e10f3c5-4564957-images-thumbs&n=13", "Большое млекопитающее, известное своей силой и величественным видом."),
        Item("seventh_id", "Волк", "https://avatars.mds.yandex.net/i?id=df5f25c93cbb3986ad5bf09a4e468b2376cfaf44-5896148-images-thumbs&n=13", "Дикий хищник, известный своей социальной структурой и охотничьими навыками."),
        Item("eighth_id", "Лиса", "https://avatars.mds.yandex.net/i?id=82a8de403bfefb8a215569166a78ea479a3d94ff-8497019-images-thumbs&n=13", "Умное и хитрое животное, часто ассоциируемое с ловкостью."),
        Item("ninth_id", "Белка", "https://avatars.mds.yandex.net/i?id=40cb2db02ffd82b6ff92ae91ca740379_l-7185373-images-thumbs&n=13", "Маленькое грызун, известный своими акробатическими способностями."),
        Item("tenth_id", "Сова", "https://avatars.mds.yandex.net/i?id=b0b79f5e0a6afe713d80dbdbce71f0ea2818e541-7043127-images-thumbs&n=13", "Ночная птица, известная своим острым зрением и характерным звуком."),
        Item("eleventh_id", "Жираф", "https://avatars.mds.yandex.net/i?id=9db3af90e8af477f0a5ba892b16e9189296ef6ba-12629451-images-thumbs&n=13", "Самое высокое наземное животное, известное своим длинным шеей."),
        Item("twelfth_id", "Слон", "https://avatars.mds.yandex.net/i?id=8851a033854823cf1b8f34a8657dd6c7f811059b-5365143-images-thumbs&n=13", "Крупное млекопитающее с характерным хоботом и большими ушами."),
        Item("thirteenth_id", "Лев", "https://avatars.mds.yandex.net/i?id=226dc2b36b03f560f22132c83cdb11dcb93c8b37-4080895-images-thumbs&n=13", "Король животных, известный своей силой и социальной структурой стаи."),
        Item("fourteenth_id", "Тигр", "https://avatars.mds.yandex.net/i?id=14a0c976efcfe10e1093c7f272e38300dbb053af-8238876-images-thumbs&n=13", "Величественный хищник с характерными полосами на шерсти."),
        Item("fifteenth_id", "Коала", "https://avatars.mds.yandex.net/i?id=bf0d3dc5808d8ce7e636e8c2a6c8c5fc-5226953-images-thumbs&n=13", "Медлительное сумчатое животное, известное своим образом жизни на деревьях."),
        Item("sixteenth_id", "Кенгуру", "https://avatars.mds.yandex.net/i?id=fc6147b63cbb7ab8d1e243a802f5db790d574431-5879090-images-thumbs&n=13", "Сумчатое животное, известное своими мощными задними ногами и прыжками."),
        Item("seventeenth_id", "Корова", "https://avatars.mds.yandex.net/i?id=59a808bc2bb33a4c0ccf0c4e274e17ad4d6de069-4406391-images-thumbs&n=13", "Домашнее животное, которое дает молоко и мясо."),
        Item("eighteenth_id", "Коза", "https://avatars.mds.yandex.net/i?id=7d2637a0c9dc8e9b21d9331dc304f0a1_l-3834231-images-thumbs&n=13", "Домашнее животное, известное своей игривой натурой."),
        Item("nineteenth_id", "Гусь", "https://avatars.mds.yandex.net/i?id=156b038e5eb89d727711dec753913315d8655359-5442358-images-thumbs&n=13", "Птица, известная своим громким криком и социальным поведением."),
        Item("twentieth_id", "Утка", "https://avatars.mds.yandex.net/i?id=59e90feed8c9793c669d3a9760a6cda19c1efc786349fa7a-11942902-images-thumbs&n=13", "Водоплавающая птица, известная своим характерным криком."),
        Item("twenty_first_id", "Курица", "https://avatars.mds.yandex.net/i?id=217b5972d74af401eaad3a458a7beae3eb5652fd-6212368-images-thumbs&n=13", "Домашняя птица, известная своим мясом и яйцами."),
        Item("twenty_second_id", "Куропатка", "https://avatars.mds.yandex.net/i?id=0f483a2c818783e0cdd3048442586c37eca071d5b308d93a-12471733-images-thumbs&n=13", "Мелкая дикая птица, известная своим вкусным мясом."),
        Item("twenty_third_id", "Черепаха", "https://avatars.mds.yandex.net/i?id=4b80bbc99d9fcc8db5819502d5afa5d814d92442-3833399-images-thumbs&n=13", "Рептилия, известная своим медленным движением и долгожительством."),
        Item("twenty_fourth_id", "Лошадь", "https://avatars.mds.yandex.net/i?id=4ec05244fda6432d002c9f04a163f66c7aa73a5b-3985701-images-thumbs&n=13", "Крупное домашнее животное, известное своей силой и грацией."),
        Item("twenty_fifth_id", "Осел", "https://avatars.mds.yandex.net/i?id=91c9dc46d435052880b58d6e068c9703200a967e65ae4a60-9847423-images-thumbs&n=13", "Домашнее животное, известное своей трудоспособностью."),
        Item("twenty_sixth_id", "Гепард", "https://avatars.mds.yandex.net/i?id=1111eac7be932cf450f27e79eea0c19e0b56b205-11385182-images-thumbs&n=13", "Самое быстрое наземное животное, известное своей скоростью."),
        Item("twenty_seventh_id", "Гиена", "https://avatars.mds.yandex.net/i?id=6f280a823314f73f401730042991eadb39feaa32-12605172-images-thumbs&n=13", "Млекопитающее, известное своим характерным смехом и социальным поведением."),
        Item("twenty_eighth_id", "Сурикат", "https://avatars.mds.yandex.net/i?id=5e57741dedd222bc2f7d6fc43e530cc6849beb9c-12937823-images-thumbs&n=13", "Социальное млекопитающее, известное своим стоячим положением."),
        Item("twenty_ninth_id", "Жук", "https://avatars.mds.yandex.net/i?id=f67e7a384cddb296ac12b4f46e5ce46c110d7210-5905988-images-thumbs&n=13", "Насекомое, известное своим появлением весной."),
        Item("thirtieth_id", "Дельфин", "https://avatars.mds.yandex.net/i?id=e9163c29f8e84f2f7ee3d1871af69d6c423d5273bc68f9cc-12935956-images-thumbs&n=13", "Умное морское млекопитающее, известное своей игривостью и дружелюбием."),
        Item("thirty_first_id", "Жаба", "https://a.d-cd.net/a6d30ees-960.jpg", "Амфибия, известная своей способностью прыгать и адаптироваться к различным условиям среды."),
        Item("thirty_second_id", "Панда", "https://cdn.culture.ru/images/b8be4de7-09da-5f22-b268-a3313756e088", "Медлительное млекопитающее, питающееся бамбуком, символ дружелюбия и защиты окружающей среды."),
        Item("thirty_third_id", "Обезьяна", "https://avatars.mds.yandex.net/i?id=1d144a33cde87579ab736efb8b658099_l-5233118-images-thumbs&n=13", "Социальное млекопитающее, известное своей умностью и игривостью, обитающее в тропических лесах.")
    )

    val items = allItems.take(15).shuffled().toMutableList()

    fun addItems(count: Int) {
        val additionalItems = allItems.shuffled().take(count)
        additionalItems.forEach { item ->
            val randomIndex = (0 until items.size + 1).random()
            items.add(randomIndex, item)
        }
    }

    fun removeItems(count: Int) {
        val itemsToRemove = minOf(count, items.size)

        val randomPositions = (0 until items.size).shuffled().take(itemsToRemove)

        randomPositions.sortedDescending().forEach { position ->
            items.removeAt(position)
        }
    }

}

