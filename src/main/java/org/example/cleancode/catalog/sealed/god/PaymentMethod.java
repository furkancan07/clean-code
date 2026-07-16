package org.example.cleancode.catalog.sealed.god;
/* sealed ile hangi classlar buınu alabilir açıkca sınırlarımızı bellirttik
* permits lsitesindeki classlar üç şeyden biri olmak zorunda: final, sealed, ya da non-sealed
* final : olmasının nedeni inheritance edilemmesi şöyle düşün biz bu interfaci sınırların çızdik oma onu implement edent bir sınıf türemmesi lazım
* sealed : permits olan sınıfında sınırlarını belirleyip kaltımı yapabilirsin
* non sealed : bunu yaprarsan permits artık kalıtım yapmaya açık hale getiröiş olursun önerilmez
* */
public sealed interface PaymentMethod permits CreditCart,BankTransfer,Crypto{
    void pay(double amount);
}
