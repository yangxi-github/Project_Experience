export class Customer {

    username: string;
    email: string;
    address: string;
    password: string;
    phoneNumber: string;
    gender: string;
    birthday: string;


    constructor(username?: string, email?: string, address?: string, country?: string,
        password?: string, phoneNumber?: string, gender?: string, birthday?: string) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthday = birthday

    }

}
