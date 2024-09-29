import React, { useState } from "react";
import RoomResult from "../commons/RoomResult";
import RoomSearch from "../commons/RoomSearch";
import bannerImage from '../../assets/banner.png';

const HomePage = () => {
    const [roomSearchResults, setRoomSearchResults] = useState([]);
    // Function to handle search results
    const handleSearchResult = (results) => {
        setRoomSearchResults(results);
    };

    return (
        <div className="home">
            {/* HEADER / BANNER ROOM SECTION */}
            <div
                className="relative w-full h-[90vh] bg-cover bg-center"
                style={{
                    height: "60vh",
                    backgroundImage: `url(${bannerImage})`,
                    backgroundSize: "cover",
                    backgroundPosition: "center",
                    backgroundRepeat: "no-repeat",
                }}
            >
                <div className="overlay"></div>
                <div className="animated-texts overlay-content">
                    <h1 className="text-center">
                        Welcome to <span className="dev-color">Book Smart</span>
                    </h1><br />
                    <h3 className="text-center uppercase">EXPERIENCE LUXURY AND COMFORT</h3>
                    <button onClick={() => window.location.href = '/rooms'} className="banner-button">Explore Rooms</button>
                </div>
            </div>
            {/* SEARCH/FIND AVAILABLE ROOM SECTION */}
            <RoomSearch handleSearchResult={handleSearchResult} />
            <RoomResult roomSearchResults={roomSearchResults} />

            <h1 className="home-services">SERVICES AT <span className="dev-color">BOOK SMART</span></h1>

            {/* SERVICES SECTION */}
            <section className="service-section">
                <div className="service-card">
                    <i className="fas fa-swimming-pool service-icon"></i>
                    <div className="service-details">
                        <h3 className="service-title">Swimming Pool</h3>
                        <p className="service-description">Take a dip in our outdoor pool, surrounded by lush greenery and offering poolside snacks and beverages.</p>
                    </div>
                </div>
                <div className="service-card">
                    <i className="fas fa-bed service-icon"></i>
                    <div className="service-details">
                        <h3 className="service-title">Luxurious Rooms</h3>
                        <p className="service-description">Relax in our spacious rooms with premium bedding, modern amenities, and a serene atmosphere.</p>
                    </div>
                </div>
                <div className="service-card">
                    <i className="fas fa-coffee service-icon"></i>
                    <div className="service-details">
                        <h3 className="service-title">Complimentary Breakfast</h3>
                        <p className="service-description">Start your day with a delicious complimentary breakfast offering a variety of local and international dishes.</p>
                    </div>
                </div>
                <div className="service-card">
                    <i className="fas fa-shuttle-van service-icon"></i>
                    <div className="service-details">
                        <h3 className="service-title">Airport Shuttle</h3>
                        <p className="service-description">Enjoy hassle-free transportation with our airport shuttle service, available upon request for all guests.</p>
                    </div>
                </div>
                <div className="service-card">
                    <i className="fas fa-laptop service-icon"></i>
                    <div className="service-details">
                        <h3 className="service-title">Business Center</h3>
                        <p className="service-description">Stay productive in our fully equipped business center with high-speed internet and conference facilities.</p>
                    </div>
                </div>
                <div className="service-card">
                    <i className="fas fa-child service-icon"></i>
                    <div className="service-details">
                        <h3 className="service-title">Kids Club</h3>
                        <p className="service-description">Our Kids Club offers fun activities and games for children, ensuring they have a great time during their stay.</p>
                    </div>
                </div>
            </section>




            {/* AVAILABLE ROOMS SECTION */}
            <section>

            </section>
        </div>
    );
}

export default HomePage;
