import React from 'react'
import homeImg from './assets/homeImg.jpeg'
import "./Home.css";
import biriyani from './assets/biriyani.jpg'
import burger from './assets/burger.webp'
import icecream from './assets/icecream.jpeg'
import starter from './assets/starter.jpg'
import indian from './assets/indian.webp'
import nonveg from './assets/nonveg.jpg'
import pannerbutter from './assets/pannerbutter.jpg'
import springRolls from './assets/springRolls.jpg'

function Home() {
  return (
    <>
      <div
        className="home-bg"
        style={{ backgroundImage: `url(${homeImg})` }}
      >
        <div className="hero-overlay">
          <div className="hero-content">
            <span className="hero-tag">🔥 India's Favorite Food Destination</span>

            <h1>
              Delicious Food <br />
              Delivered To Your Door
            </h1>

            <p>
              From spicy biryanis to mouth-watering burgers,
              enjoy your favorite meals anytime, anywhere.
            </p>

          </div>
        </div>
      </div>
      <section className="container py-5">
        <h2 className="text-center mb-5">
          Why Choose FoodMania?
        </h2>

        <div className="row g-4">

          <div className="col-md-4">
            <div className="feature-card">
              <h1>🚚</h1>
              <h4>Fast Delivery</h4>
              <p>Get your favorite meals delivered quickly.</p>
            </div>
          </div>

          <div className="col-md-4">
            <div className="feature-card">
              <h1>🍜</h1>
              <h4>Fresh Food</h4>
              <p>Prepared with quality ingredients every day.</p>
            </div>
          </div>

          <div className="col-md-4">
            <div className="feature-card">
              <h1>⭐</h1>
              <h4>Top Rated</h4>
              <p>Thousands of happy customers trust us.</p>
            </div>
          </div>

        </div>
      </section>
      <section className='py-5'>
        <h2 className="text-center mb-4">
          Our Most Loved Dishes ❤️
        </h2>

        <div className="slider">
          <div className="slide-track">

            <img src={biriyani} alt=""/>
            <img src={burger} alt=""/>
            <img src={icecream} alt=""/>
            <img src={starter} alt=""/>
            <img src={nonveg} alt=""/>
            <img src={indian} alt=""/>
            <img src={pannerbutter} alt=""/>
            <img src={springRolls} alt=""/>

            <img src={biriyani} alt=""/>
            <img src={burger} alt=""/>
            <img src={icecream} alt=""/>
            <img src={starter} alt=""/>
            <img src={nonveg} alt=""/>
            <img src={indian} alt=""/>
            <img src={pannerbutter} alt=""/>
            <img src={springRolls} alt=""/>

          </div>
        </div>
      </section>
        
        <section className="stats-section">
          <div className="container">
            <div className="row text-center">

              <div className="col-md-3">
                <h2>5000+</h2>
                <p>Orders Delivered</p>
              </div>

              <div className="col-md-3">
                <h2>200+</h2>
                <p>Food Items</p>
              </div>

              <div className="col-md-3">
                <h2>1000+</h2>
                <p>Happy Customers</p>
              </div>

              <div className="col-md-3">
                <h2>4.8⭐</h2>
                <p>Average Rating</p>
              </div>

            </div>
          </div>
        </section>

      <footer className="bg-dark text-light text-center p-3 " style={{fontWeight:'bolder', fontFamily:'times-new-roman'}}>
        <p className='mb-0'>
            © 2026 OnlineFoodApp. All Rights Reserved.
        </p>
      </footer>
    </>
  )
}

export default Home  
