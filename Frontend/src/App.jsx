import React from 'react'
import SignIn from './component/Pages/SignIn'
import { RouterProvider } from 'react-router-dom'
import { routingPage } from './component/Router/RoutingPage'
import { authStore } from './component/Redux/Auth/authStore'
import { Provider } from 'react-redux'

const App = () => {
  return (
    <div className='bg-[#0d0d0d] '>
      <Provider store={authStore }>
      <RouterProvider router={routingPage}>
      </RouterProvider>
      </Provider>

    </div>
  )
}

export default App